(defproject ocr-service "0.1.0-SNAPSHOT"
  :description "Simple OCR service"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [net.sourceforge.tess4j/tess4j "4.4.0"]
                 [org.clojure/tools.namespace "0.3.1"]
                 [http-kit "2.3.0"]]
  
  :plugins [[lein-ring "0.12.5"]
            [lein-ancient "0.6.15"]]
  :main ocr-service.handler
  :ring {:handler ocr-service.handler/app
         :nrepl { :start true  :port 6000}}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.4.0"]
                        [clj-http "3.10.0"]]}})
