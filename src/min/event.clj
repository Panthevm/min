(ns min.event
  (:require
   min.api.event
   min.item-counter))

(min.api.event/listen
 :server-list-ping
 (fn [event]
   (.setMotd event "Hello world")))

(min.api.event/listen
 :player-join
 (fn [event]
   (.setJoinMessage event "")))

(min.api.event/listen
 :entity-death
 (fn [event]
   (when (instance? org.bukkit.event.entity.PlayerDeathEvent event)
     (.setDeathMessage event ""))))

(min.api.event/listen
 :item-spawn
 (fn [event]
   (min.item-counter/on-item-spawn (.getEntity event))))

(min.api.event/listen
 :item-merge
 (fn [event]
   (min.item-counter/on-item-merge (.getTarget event)
                                   (.getEntity event))))


(comment
  (def p (->> (min.api.plugin/get-server) .getOnlinePlayers first))
  
  (.sendRawMessage p (chat [:white [:gold "Panthevm"] [:gray " : "] "Hello World"]))

  (min.api.schedule/run-task
   (fn [] 
     (.setGameMode p org.bukkit.GameMode/CREATIVE)))

  (min.api.event/unregister-all)
  (min.api.event/get-registered-listeners))
