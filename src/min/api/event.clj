(ns min.api.event
  (:require
   min.api.plugin)
  (:import
   clojure.lang.Reflector
   org.bukkit.plugin.EventExecutor
   org.bukkit.event.server.ServerListPingEvent
   org.bukkit.event.player.PlayerCommandPreprocessEvent
   org.bukkit.event.player.PlayerJoinEvent
   org.bukkit.event.block.BlockDropItemEvent
   org.bukkit.event.entity.EntityDropItemEvent
   org.bukkit.event.entity.ItemMergeEvent
   org.bukkit.event.Listener
   org.bukkit.event.EventPriority
   org.bukkit.event.HandlerList))

(defn get-event-class
  [event-name]
  (case event-name
    :server-list-ping org.bukkit.event.server.ServerListPingEvent
    :player-death     org.bukkit.event.entity.PlayerDeathEvent
    :player-join      org.bukkit.event.player.PlayerJoinEvent
    :entity-death     org.bukkit.event.entity.EntityDeathEvent
    :item-merge       org.bukkit.event.entity.ItemMergeEvent
    :item-spawn       org.bukkit.event.entity.ItemSpawnEvent))

(defn get-event-handler
  [event-name]
  (->
   (get-event-class event-name)
   (clojure.lang.Reflector/invokeStaticMethod "getHandlerList" (into-array []))))

(defn unlisten
  [event-name]
  (.unregister (get-event-handler event-name) min.api.plugin/instance))

(defn listen
  [event-name callback]
  (unlisten event-name)
  (.registerEvent
   (min.api.plugin/get-manager)
   (get-event-class event-name)
   (with-meta (reify org.bukkit.event.Listener)
     {::event event-name})
   org.bukkit.event.EventPriority/NORMAL
   (reify org.bukkit.plugin.EventExecutor
     (execute [this listener event]
       (callback event)))
   min.api.plugin/instance))

(defn unregister-all
  []
  (org.bukkit.event.HandlerList/unregisterAll min.api.plugin/instance))

(defn get-registered-listeners
  []
  (->>
   (org.bukkit.event.HandlerList/getRegisteredListeners min.api.plugin/instance)
   (map
    (fn [listener]
      (let [metadata (-> listener .getListener meta)]
        {:event    (::event metadata)
         :priority (str (.getPriority listener))})))))
