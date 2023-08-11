# undirected-facebook-graph
Example of an undirected graph, that may be used by a social media website such as Facebook

- addEdges() allows you to add a distance between nodes
  - Distance values between node profiles may reflect the strength of friendship between nodes that can be determined by another algorithim in the future by calculating a similarity coefficient based on interests.
- Dijkstra's shortest path algorithm displays the shortest path from a given node to all the other nodes, as well as the prev node visited in the path
  - If distance values represent friend strength, we can use this algorithim to "contact" other profile nodes that edges are not shared with via friend nodes whom we are the closest friends with
- suggestFriends() suggests a list of people whom a node is not friends with, by looking at its current friends and then friends of current friends, whom they are not friends (or share edges) with
