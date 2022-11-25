# Scores

Program to calculate the scores of players depending on how they perform in a tournament of different games.

## Description

There is a team building event where coworkers compete in several games. The games played are ***paintball*** and ***karting***, but they plan to include more games in the future.

Company's engineers have been contacted to create a program to obtain the scores of the players according to their performance stats in each game of the event.

The program will receive the player's stats for each game. Each stat will give a different amount of points. The scores should be calculated by adding the points obtained in all games.

A player will receive extra points if their team won the game. One player may play in different teams and positions in different games, but not in the same game.

If the provided data is invalid or has an incorrect format, the program should exit with an error.

### Paintball

Each row will represent one player stats in a paintball match, with this format:

```csv
# Paintball game format
player name;team name;position;flags captured;kills;deaths
```

```csv
# Example paintball game
Tyrion;Lannister;Back;0;5;4
Bronn;Lannister;Mid;0;8;3
Jaime;Lannister;Front;10;4;7
Varys;Stark;Back;0;5;2
Hodor;Stark;Mid;2;7;8
Arya;Stark;Front;10;2;7
```

This table shows the points each player receives in a paintball match depending on their position on the field:

| Position | Flags | Kills | Deaths |
|----------|:-----:|:-----:|:------:|
| Back     |   3   |   1   |   -3   |
| Mid      |   2   |   2   |   -2   |
| Front    |   1   |   3   |   -1   |

E.g., a player playing as a "Front" with 10 flags captured, 1 kills and 3 deaths will be given 10x1 + 1x3 + 3x(-1) = 10 points

---
⚠️️ **IMPORTANT:** _The winner team is the one with the most captured flags, and its members will receive *10 extra points*._

---

### Karting

Each row will represent one player stats in a kart race, with this format:

```csv
# Kart race format
player name;team name;rank;finish position;positions gained;fastest laps
```

```csv
# Example kart race
Tyrion;Lannister;Fast;1;0;15
Jaime;Lannister;Normal;3;-1;1
Varys;Lannister;Slow;6;-1;0
Arya;Stark;Fast;2;1;4
Bronn;Stark;Normal;4;0;0
Hodor;Stark;Slow;5;1;0
```

This table shows the points each player receives in the kart race depending on their speed within the team:

| Rank   | Finish position | Positions gained | Fastest laps |
|--------|:---------------:|:----------------:|:------------:|
| Fast   |       -3        |        1         |      1       |            
| Normal |       -2        |        1         |      2       |          
| Slow   |       -1        |        1         |      3       |         


E.g., a "Normal" driver who finished second, gained 2 positions and with 2 fastest lap during the race will be given 2x(-2) + 2x1 + 2x2 = 2 points

---
⚠️️ **IMPORTANT:** _The winner team is the one with the better overall finish position (lower is better), and its members will receive *1 extra point*._

---

### Expected scores

These are the scores for the previous games' data:

| Player | Score |
|--------|:-----:|
| Bronn  |   2   |             
| Varys  |   3   |
| Tyrion |   6   |
| Hodor  |   8   |
| Jaime  |  11   |
| Arya   |  18   |