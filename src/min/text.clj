(ns min.text)

(def colors
  {:black        {:chat "§0"}
   :dark-blue    {:chat "§1"}
   :dark-green   {:chat "§2"}
   :dark-aqua    {:chat "§3"}
   :dark-red     {:chat "§4"}
   :dark-purple  {:chat "§5"}
   :gold         {:chat "§6"}
   :gray         {:chat "§7"}
   :dark-gray    {:chat "§8"}
   :blue         {:chat "§9"}
   :green        {:chat "§a"}
   :aqua         {:chat "§b"}
   :red          {:chat "§c"}
   :light-purple {:chat "§d"}
   :yellow       {:chat "§e"}
   :white        {:chat "§f"}})

(def formats
  {:obfuscated    {:chat "§k"}
   :bold          {:chat "§l"}
   :strikethrough {:chat "§m"}
   :underline     {:chat "§n"}
   :italic        {:chat "§o"}
   :reset         {:chat "§r"}})

(defn format-schema
  [format-type node & [parent-color parent-format]]
  (if (sequential? node)
    (let [tag    (first node)
          color  (get-in colors  [tag format-type] parent-color)
          format (get-in formats [tag format-type] parent-format)]
      (->> (next node)
           (map #(format-schema format-type % color format))
           (apply str)))
    (str (get-in formats [:reset format-type]) parent-color parent-format node)))

(defn chat
  [schema]
  (format-schema :chat schema))
