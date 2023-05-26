(ns malli.concerto
                (:require [malli.experimental.time :as met]
                          [malli.experimental.time.generator]
                          [jsonista.core :as j]
                          [malli.core :as m]))

;; read json-ast from a source with keyword-keys
(defn read-ast [source] (j/read-value source j/keyword-keys-object-mapper))

;; map Concerto types into malli schemas (not complete)
(def type-mapping {"concerto.metamodel@1.0.0.StringProperty" :string
                   "concerto.metamodel@1.0.0.IntegerProperty" :int
                   "concerto.metamodel@1.0.0.BooleanProperty" :boolean
                   "concerto.metamodel@1.0.0.DoubleProperty" :double
                   "concerto.metamodel@1.0.0.DateTimeProperty" :date-time})

;; miminal subset of malli that is supported
(def default-registry {:map (m/-map-schema)
                       :string (m/-string-schema)
                       :double (m/-double-schema)
                       :boolean (m/-boolean-schema)
                       :int (m/-int-schema)
                       :enum (m/-enum-schema)
                       :date-time (met/-zoned-date-time-schema)
                       := (:= (m/comparator-schemas))})

;; create a malli registry out of Concerto AST
(defn transform [ast]
  (let [{:keys [_decorators _imports _$class declarations namespace]} ast]
    (->> (for [{:keys [name properties $class] :as declaration} declarations]
           (let [concerto (-> declaration (dissoc :properties) (assoc :namespace namespace))]
             (case $class
               "concerto.metamodel@1.0.0.ConceptDeclaration"
               [name (into [:map {:concerto concerto} [:$class [:= (str namespace "." name)]]]
                           (for [{:keys [name _isArray type $class isOptional]} properties]
                             (let [type (type-mapping $class (:name type))]
                               (-> [(keyword name)]
                                   (cond-> isOptional (conj {:optional true}))
                                   (conj type)))))]
               "concerto.metamodel@1.0.0.EnumDeclaration"
               [name (into [:enum {:concerto concerto}] (for [{:keys [name]} properties] name))])))
         (into {}))))

;; with default schemas
(defn registry [registry] (merge default-registry registry))

;; create Schema instance
(defn schema [name schemas] (m/schema name {:registry (registry schemas)}))