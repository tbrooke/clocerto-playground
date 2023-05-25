(ns concerto
  (:require [clojure.java.io :as io]
            [jsonista.core :as j]
            [malli.core :as m]))


;; read json-ast from a source with keyword-keys
(defn read-ast [source] (j/read-value source j/keyword-keys-object-mapper))

(read-ast (io/file "src/person.json"))
;; (read-ast (io/file "library/ast/metamodel.json"))
;; (read-ast (io/file "library/ast/money@0.2.0.ast.json"))

(defn clean [data]
  (clojure.walk/prewalk (fn [node] (if (map? node)
                                     (apply dissoc node [:location ])
                                     node))
                        data))

(clean (read-ast (io/file "library/ast/cicerodom@0.2.0.ast.json")))
(clean (read-ast (io/file "library/ast/ciceromark@0.6.0.ast.json")))


;; map Concerto types into malli schemas (not complete)
(def type-mappping {"concerto.metamodel@1.0.0.StringProperty"  :string
                    "concerto.metamodel@1.0.0.IntegerProperty" :int
                    "concerto.metamodel@1.0.0.DoubleProperty"  :double
                    "concerto.metamodel@1.0.0.BooleanProperty" :boolean
                    })

;; miminal subset of malli that is supported
(def default-registry {:map (m/-map-schema)
                       :string (m/-string-schema)
                       :int (m/-int-schema)
                       :double (m/-double-schema)
                       :boolean (m/-boolean-schema)
                       := (:= (m/comparator-schemas))})


;; create a malli registry out of Concerto AST
(defn transform [ast]
  (let [{:keys [_decorators _imports _$class declarations namespace]} ast]
    (->> (for [{:keys [name properties] :as declaration} declarations]
           (let [concerto (-> declaration (dissoc :properties) (assoc :namespace namespace))
                 $class (str namespace "." name)]
             [name (into [:map {:concerto concerto} [:$class [:= $class]]]
                         (for [{:keys [name _isArray type $class isOptional]} properties]
                           (let [type (type-mappping $class (:name type))]
                             (-> [(keyword name)]
                                 (cond-> isOptional (conj {:optional true}))
                                 (conj type)))))]))
         (into (merge default-registry)))))

;;
;; spike
;;

;; transform Concerto file
(def person (transform (read-ast (io/file "src/person.json"))))
(def org (transform (clean (read-ast (io/file "library/ast/organization.ast.json")))))
                                                           
person
;; => {:map #IntoSchema{:type :map},
;;     :string #IntoSchema{:type :string},
;;     :int #IntoSchema{:type :int},
;;     :double #IntoSchema{:type :double},
;;     :boolean #IntoSchema{:type :boolean},
;;     := #IntoSchema{:type :=},
;;     "Address"
;;     [:map
;;      {:concerto
;;       {:isAbstract false,
;;        :name "Address",
;;        :$class "concerto.metamodel@1.0.0.ConceptDeclaration",
;;        :namespace "org.acme@1.0.0"}}
;;      [:$class [:= "org.acme@1.0.0.Address"]]
;;      [:street :string]],
;;     "Person"
;;     [:map
;;      {:concerto
;;       {:isAbstract false,
;;        :name "Person",
;;        :$class "concerto.metamodel@1.0.0.ConceptDeclaration",
;;        :namespace "org.acme@1.0.0"}}
;;      [:$class [:= "org.acme@1.0.0.Person"]]
;;      [:name :string]
;;      [:age :int]
;;      [:adress {:optional true} "Address"]]}

org
;; => {:map #IntoSchema{:type :map},
;;     :string #IntoSchema{:type :string},
;;     :int #IntoSchema{:type :int},
;;     :double #IntoSchema{:type :double},
;;     :boolean #IntoSchema{:type :boolean},
;;     := #IntoSchema{:type :=},
;;     "Organization"
;;     [:map
;;      {:concerto
;;       {:isAbstract false,
;;        :name "Organization",
;;        :$class "concerto.metamodel.ParticipantDeclaration",
;;        :identified {:name "identifier", :$class "concerto.metamodel.IdentifiedBy"},
;;        :namespace "org.accordproject.organization"}}
;;      [:$class [:= "org.accordproject.organization.Organization"]]
;;      [:identifier nil]
;;      [:name {:optional true} nil]
;;      [:description {:optional true} nil]
;;      [:duns {:optional true} nil]
;;      [:place {:optional true} "Place"]]}


;; concrete Person Malli Schema object
(def Person
 (m/schema "Person" {:registry person}))

;; (def Org 
;;   (m/schema "Org" {:registry org}))

;; Source of Malli error
;; (defn -exception
;;   ([type] (-exception type nil))
;;   ([type data] (ex-info (str type) {:type type, :message type, :data data})))

(require '[malli.generator :as mg])

;; generate 10 samples
(mg/sample Person {:seed 0, :size 10})
;({:$class "org.acme@1.0.0.Person", :name "", :age 0}
; {:$class "org.acme@1.0.0.Person", :name "M", :age -1, :adress {:$class "org.acme@1.0.0.Address", :street "d"}}
; {:$class "org.acme@1.0.0.Person", :name "5U", :age 0}
; {:$class "org.acme@1.0.0.Person", :name "Gi4", :age 1, :adress {:$class "org.acme@1.0.0.Address", :street ""}}
; {:$class "org.acme@1.0.0.Person", :name "o", :age -1}
; {:$class "org.acme@1.0.0.Person", :name "hZ21u", :age 1}
; {:$class "org.acme@1.0.0.Person", :name "Z3A", :age 19}
; {:$class "org.acme@1.0.0.Person", :name "13", :age 23}
; {:$class "org.acme@1.0.0.Person", :name "", :age 1}
; {:$class "org.acme@1.0.0.Person", :name "z5xKOF", :age -8})

;; all values are valid against a schema
(every? (m/validator Person) *1)
; => true

(require '[malli.error :as me])

;; validation
(->> {:$class "org.acme@1.0.0.Person"
      :name "Tommi"
      :age "49"
      :adress {:$class "org.acme@1.0.0.Addrezz"
               :street "Torpparintie 4"}}
     (m/explain Person)
     (me/humanize))
;{:age ["should be an integer"]
; :adress {:$class ["should be org.acme@1.0.0.Address"]}}