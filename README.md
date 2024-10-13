Two-Player Dice Game
This Java project implements a console-based, two-player dice game as part of the CST2110 Summative Assessment.
Game Rules

Players: 2 players take turns.
Rounds: The game is played over exactly 7 rounds (7 turns per player).
Dice: 5 dice are used in each turn.
Objective: Achieve the highest cumulative score after 7 rounds.

Turn Structure

Each turn allows up to 3 throws of the dice.
After the first throw, a player can:
a) Set aside one or more dice and choose a category, or
b) Defer the throw and re-roll all 5 dice.

Categories and Scoring

Number Categories (Ones through Sixes):

Score = (Number of dice showing that number) × (Category number)
Example: Three 5s in the "Fives" category scores 15 points (5 × 3).


Sequence Category:

Valid sequences are exactly 1-2-3-4-5 or 2-3-4-5-6 in that order.
Score 20 points for a valid sequence.
Score 0 points if the sequence is not achieved.



Special Rules

Players must choose a different category for each of their 7 turns.
If all 5 dice are set aside after 1 or 2 throws, the turn ends immediately.
Players can defer twice, but must select a category on the final throw.
If no dice match the chosen category, the player scores 0 for that category.

Technical Details

Implemented in Java 8
Console-based interface
Developed as a NetBeans project

How to Run

Clone the repository
Open the project in NetBeans
Run the main class to start the game

Test Mode
This game includes a test mode for easier verification of game logic:

When prompted at the start, enter 'test' to activate test mode.
In test mode, you can manually input dice rolls.
The program will also run automatic tests for the sequence logic.
