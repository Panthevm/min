(ns min.api.plugin
  (:import
   org.bukkit.Bukkit
   org.bukkit.plugin.PluginBase
   org.bukkit.plugin.PluginDescriptionFile))

(def instance
  (proxy [org.bukkit.plugin.PluginBase] []
    (getDescription []
      (org.bukkit.plugin.PluginDescriptionFile. "witchcraft" "1.0" ""))
    (getServer []
      (org.bukkit.Bukkit/getServer))
    (isEnabled []
      true)))

(defn get-server
  []
  (.getServer instance))

(defn get-scheduler
  []
  (.getScheduler (get-server)))

(defn get-manager
  []
  (.getPluginManager (get-server)))
