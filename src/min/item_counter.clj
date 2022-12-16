(ns min.item-counter
  (:require clojure.string)
  (:import org.bukkit.entity.Item))

(defn format-amount
  [amount]
  (str "§6ˣ"
       (clojure.string/replace
        amount
        #"1|2|3|4|5|6|7|8|9|0"
        {"1" "¹"
         "2" "²"
         "3" "³"
         "4" "⁴"
         "5" "⁵"
         "6" "⁶"
         "7" "⁷"
         "8" "⁸"
         "9" "⁹"
         "0" "⁰"})))

(defn get-item-amount
  [item]
  (.getAmount (.getItemStack item)))

(defn set-item-count
  [item amount]
  (when (> amount 1)
    (doto item
      (.setCustomName (format-amount amount))
      (.setCustomNameVisible true))))

(defn on-item-spawn
  [item]
  (when (instance? org.bukkit.entity.Item item)
    (set-item-count item (get-item-amount item))))

(defn on-item-merge
  [target entity]
  (set-item-count target (+ (get-item-amount target)
                            (get-item-amount entity))))

