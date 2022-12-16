(ns min.api.schedule
  (:require
   min.api.plugin)
  (:import
   org.bukkit.Bukkit))

(defn run-task
  [callback]
  (.runTask
   (min.api.plugin/get-scheduler)
   min.api.plugin/instance
   callback))

(defn sync-delayed-task
  [callback]
  (.scheduleSyncDelayedTask
   (min.api.plugin/get-scheduler)
   min.api.plugin/instance
   callback))
