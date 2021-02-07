# Zendesk Backend Exercise

## Description
A standalone application that takes in inputs for origin and destination railway stations,
and prints one or more available routes sorted by time (ascending).

## Installation
Ensure you have Java 11 or above installed in your system. Thereafter, you should be able
to run the .jar file found in the
[releases page](https://github.com/khooroko/ZD_BE_Exercise/releases).
Alternatively, you can clone this repository and run Main#main in any IDE of your choice,
or compile the program from the source code. However, you would need to download
[jodatime](https://www.joda.org/joda-time/installation.html).

## Usage
To run the .jar file from the command line, navigate to the directory and run
```
java -jar ZD_BE_Exercise.jar
```
After opening the .jar file, input the origin station, destination station, and desired 
start time in a single line in the following format: `<Origin station code> <Destination 
station code> <Start time in "YYYY-MM-DDThh:mm" format>`. For example,
```
EW1 CC9 2019-01-31T16:00
```

The available routes with instructions will then be printed in order of ascending time
taken to reach the destination.

Routes that take more than 1.5x the duration of the route with the shortest duration will
not be shown.

If no routes are available, there will also be a message
to indicate as such.

The output for the example input provided above is:
```
Routes sorted by travel time
Route 0:
Take EW line from [EW1] Pasir Ris to [EW8] Paya Lebar
Change from EW line to CC line
Time taken: 80 minutes

Route 1:
Take EW line from [EW1] Pasir Ris to [EW2] Tampines
Change from EW line to DT line
Take DT line from [DT32] Tampines to [DT26] MacPherson
Change from DT line to CC line
Take CC line from [CC10] MacPherson to [CC9] Paya Lebar
Time taken: 88 minutes
```

To exit the app, simply input `exit`.

## Assumptions
1. When starting at an interchange, waiting time will still be included if the provided
   station code is not of the same line as the first train to take. E.g: if the first
   train to take is on the CC line, but the input source station is EW8, it will take
   time to first change to CC9.
2. Stations will not change, i.e. there will not be new stations added to this system
   while the user is still using this version.
3. Time of day only affects travel time when starting off at a station, i.e. if a user
   starts at 5.59pm, the non-peak travel time will be used when travelling out of the
   station. Peak hour time costs only kick in from the next station onwards.
   Furthermore, if the user is at a station while the line is available, the
   user will be able to take the train. E.g. taking the DT line for one stop at 9.59pm
   is valid.
4. Circle line is not closed.
5. Stations in the csv file are sorted by line (all stations of the same line are 
   together), followed by number (each station following the previous will be reachable
   by taking that line for one stop).
   
## Design Considerations
- DFS is used with a quick return pruning heuristic
    - If time taken is more than <constant> (currently 1.5x) times of the current shortest time, 
      stop considering the current path
    - DFS is used because the best case, and the average case are faster than those of BFS,
      while the worst case can be helped via pruning
- Since the railway map is static, it should be possible to save all viable paths from every
  station to every other station, and decide the duration for the path based on the time of
  day with input afterwards. However, this will result in the app becoming pretty big, so this
  was not used.
- Due to moving between stations being dynamic (different duration at different times of day),
  implementation for a suitable memoization becomes very complicated, and hence was not used.

## Future plans (things not done due to time constraint)
- Improve worst case run time (via adding more quick returns or by making use of 
  interchanges and terminal stations as separate data structures)
- Consider using Epstein's algorithm (1997)
- Allow updating the csv file
- Improve input validation
- Improve/add unit testing (use Mockito)
- Add system tests
- Allow user to input different sort heuristics, e.g. by least number of transfers
