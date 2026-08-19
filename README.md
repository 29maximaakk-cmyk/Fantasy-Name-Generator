🧙 Fantasy Name Generator — Multi‑Language Random Name Creator
8 languages, one magical name generator – create unique fantasy names for elves, orcs, humans, dragons, and more – right from your terminal.

✨ Features
🧝 Multiple races – Elf, Orc, Human, Dragon, and "Any" (mixed)

🧑‍🤝‍🧑 Gender selection – male, female, or any

🔢 Count control – generate 1 to 100+ names at once

📏 Length control – set minimum and maximum name length

💾 Save to file – output names to a text file (one per line)

🎲 Syllable‑based generation – creates believable fantasy names

🌐 UTF‑8 support – handles special characters (accents, etc.)

🧰 Supported Languages & Files
Language	File
Python	fantasy_name_gen.py
Go	fantasy_name_gen.go
JavaScript (Node)	fantasy_name_gen.js
Ruby	fantasy_name_gen.rb
PHP	fantasy_name_gen.php
Java	FantasyNameGen.java
C#	FantasyNameGen.cs
C++	fantasy_name_gen.cpp
🚀 Common Usage
All implementations follow the same CLI pattern:

bash
# Generate one random name
<command> -n 1

# Generate 5 elven female names
<command> -n 5 -r elf -g female

# Generate 10 orc names and save to file
<command> -n 10 -r orc -o orc_names.txt

# Generate names with length between 4 and 8 characters
<command> -n 3 --min 4 --max 8
Arguments:

-n, --count – number of names (default: 1)

-g, --gender – male, female, or any (default: any)

-r, --race – elf, orc, human, dragon, or any (default: any)

-o, --output – output file (optional)

--min – minimum length (optional)

--max – maximum length (optional)

📸 Example Output
text
🧙 Fantasy Name Generator
Race: Elf, Gender: female, Count: 3
1. Aelindra
2. Lúthien
3. Galadriel
📁 Repository Structure
text
.
├── README.md
├── python/
│   └── fantasy_name_gen.py
├── go/
│   └── fantasy_name_gen.go
├── javascript/
│   └── fantasy_name_gen.js
├── ruby/
│   └── fantasy_name_gen.rb
├── php/
│   └── fantasy_name_gen.php
├── java/
│   └── FantasyNameGen.java
├── csharp/
│   └── FantasyNameGen.cs
└── cpp/
    └── fantasy_name_gen.cpp
