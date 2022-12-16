server-jar      := https://api.papermc.io/v2/projects/paper/versions/1.19.3/builds/328/downloads/paper-1.19.3-328.jar
server-jar-path := paper.jar

witchcraft-plugin-jar := https://github.com/lambdaisland/witchcraft-plugin/releases/download/v0.7.35/witchcraft-plugin-0.7.37-for-paper-1.18.jar
witchcraft-jar-path   := plugins/witchcraft.jar

init:
	curl -L $(server-jar) > $(server-jar-path) \
        && curl -L $(witchcraft-plugin-jar) > $(witchcraft-jar-path)

run:
	java -jar $(server-jar-path) --nogui

truncate:
	rm -rf banned-ips.json banned-players.json bukkit.yml cache commands.yml config help.yml libraries logs ops.json paper.jar permissions.yml spigot.yml usercache.json version_history.json versions whitelist.json world world_nether world_the_end plugins/bStats plugins/witchcraft.jar .nrepl-port	
