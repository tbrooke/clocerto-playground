(ns playground
  (:require [malli.concerto :as c]
            [clojure.java.io :as io]))



(c/read-ast (io/file "src/person.json"))

;; remove location from the AST - It is no longer used
;; This can be done from the command line

(defn clean [data]
  (clojure.walk/prewalk (fn [node] (if (map? node)
                                     (apply dissoc node [:location])
                                     node))
                        data))



(c/read-ast (clean (io/file "library/ast/metamodel.json")))
(def metamodel (c/read-ast  (clean (io/file "library/ast/metamodel@1.0.0.ast.json"))))
metamodel

;;
;; Large models cicerodom and ciceromark

(clean (c/read-ast (io/file "library/ast/cicerodom@0.2.0.ast.json")))
(clean (c/read-ast (io/file "library/ast/ciceromark@0.6.0.ast.json")))
(clean (c/read-ast (io/file "library/ast/organization.ast.json"))))
(clean (c/read-ast (io/file "library/ast/obligation.ast.json"))))
(clean (c/read-ast (io/file "library/ast/runtime.ast.json"))))
(clean (c/read-ast (io/file "library/ast/runtime.ast.json"))))





;; transform Concerto metamodel file

(def metamodel (c/transform (clean (c/read-ast (io/file "library/ast/metamodel.json"))))))

metamodel



(def person (c/transform (c/read-ast (io/file "src/person.json"))))

person

(def obligation (c/transform (c/read-ast (io/file "library/ast/obligation.ast.json"))))
;; No matching clause: concerto.metamodel.EventDeclaration

(def state (c/transform (c/read-ast (io/file "library/ast/state@0.2.0.ast.json"))))

;; No matching clause: concerto.metamodel.EnumDeclaration

(def contract
     ;; => #object[clojure.lang.Var$Unbound 0x2c2a5b99 "Unbound: #'playground/contract"]
 (c/transform (c/read-ast (io/file "library/ast/contract@0.2.0.ast.json"))))

;; Experiments with merge

(require '[malli.util :as mu])


(def bond
 (let [bond (c/read-ast
             (io/file "library/ast/bond@0.3.0.ast.json"))
       metamodel (c/read-ast (io/file "library/ast/metamodel@1.0.0.ast.json"))]
   (mu/-merge bond metamodel)))

(def metabond
  ())

;; No matching clause: concerto.metamodel@1.0.0.AssetDeclaration


(def org (c/transform (clean (c/read-ast (io/file "library/ast/organization.ast.json")))))
;; :$class "concerto.metamodel.ParticipantDeclaration",

(def mark (c/transform (clean (c/read-ast (io/file "library/ast/ciceromark@0.6.0.ast.json")))))

mark

(def run (c/transform (clean (c/read-ast (io/file "library/ast/runtime.ast.json")))))
;; No matching clause: concerto.metamodel.TransactionDeclaration

(def party (c/transform (clean (c/read-ast (io/file "library/ast/party.ast.json")))))
;; No matching clause: concerto.metamodel.ParticipantDeclaration

;;  [{:properties [], :isAbstract false, :name "Request", :$class "concerto.metamodel.TransactionDeclaration"}
;; {:properties [], :isAbstract false, :name "Response", :$class "concerto.metamodel.TransactionDeclaration"}

(def mark (c/transform (clean (c/read-ast (io/file "library/ast/ciceromark@0.6.0.ast.json")))))

mark

(def dom
     ;; => #object[clojure.lang.Var$Unbound 0x34ad5775 "Unbound: #'playground/dom"]
 (c/transform (clean (c/read-ast (io/file "library/ast/cicerodom@0.2.0.ast.json")))))