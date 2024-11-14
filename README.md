# ECSE 321 Team 12 (Reindeer Games, Inc 🦌)

## The Project
An online game store (full stack web application).\
Project scope: requirements elicitation and documentation, persistence layer, database, backend, frontend.

**A message from the team:**

Hi there! We appreciate your interest in our project. Here is a brief introduction from each member of the team: 


James: "Hi. I am a U2 Computer Engineering student at McGill. A fun fact about me is that I speak Scottish Gaelic."

Kennedy: "Hey! I am a U2 Computer Engineering student at McGill. A fun fact about me is that I LOVE bubble tea!"

Amy: "Hello there! I am a U2 Software Engineering student at McGill. A fun fact about me is that I have never broken a bone."

Carmin: "Hey! I am a U2 Computer Engineering student at McGill. A fun fact about me is that I love green bananas."

Sophia: "Hi! I am a U2 Software Engineering student at McGill. A fun fact about me is that I play the piano."

Julien: "Hey. I am a U2 Software Engineering student at McGill! A fun fact about me is I am from France."


### See project reports and meeting minutes on our [Wiki](https://github.com/McGill-ECSE321-Fall2024/project-group-12/wiki)

## Team Members
### Deliverable 1
| Name             | Team Roles       | Hours |
| ---------------- | ---------------- | --------------------- |
| James Madden     | Tech Lead        |  18                   |
| Sophia Li        | Project Manager  |  18                   |
| Amy Ding         | UI/UX Designer   |  18                   |
| Julien Heng      | Developer        |  13                   |
| Kennedy Olsen    | Business Analyst |  18                   |
| Carmin Vidé | Scrum Master     |  18                   |

### Deliverable 2
| Name             | Team Roles       | Hours | Individual Effort |
| ---------------- | ---------------- | ----- | ---- |
| James Madden     | Tech Lead        |  23   | <ul><li> Implemented order related use cases</li><li> Implemented authentication</li></ul>|
| Sophia Li        | Project Manager  |  15   | <ul><li> Implemented cart related use cases</li><li>Implemented test coverage</li></ul> |
| Amy Ding         | UI/UX Designer   |  19   | <ul><li> Implemented employee related use cases</li><li>Updated Wiki for Software Quality Assurance Plan and Report</li><li>Took meeting minutes</li><li>Implemented manager related use cases</li><Add various unit tests<li/></ul>|
| Julien Heng      | Developer        |  15   | <ul><li> Implemented game related use cases</li><li> Implemented discounts on orders</li><li> Implemented wishlist related business logic and tests</li><li> Updated API endpoints documentation</li></ul> |
| Kennedy Olsen    | Business Analyst |  12.5 | <ul><li> Implemented review related use cases</li><li> Updated Wiki for Quality Assurance Report</li></ul> |
| Carmin Vidé | Scrum Master     |   12  | <ul><li> Implemented customer and comment related use cases</li><li> Updated Wiki for Quality Assurance Report</li></ul>|



## Contributing
To ensure high quality, well tested code, do the following when contributing to this project:  
1. Clone the project locally.
2. Create a branch: `name/feature`
3. Push your code to that branch.
4. When you're done, run `bash ./gradlew format` (or `./gradlew.bat format` for Windows)
5. Create a pull request and assign another team member to review your code.
6. Once they've reviewed your code, they'll merge it into main!

## Run the Project
For the user authentication, you must create a `.env` file in the root of the project, and enter in `JWT_SECRET="<...>"`. Replace with any secret you'd like.  

Start the frontend:
```
npm start
```
Start the backend:\
Unix:
```
bash ./gradlew bootRun
```
Windows:
```
./gradlew.bat bootRun
```

## Test the Project
Test the backend:\
Unix:
```
bash ./gradlew test
```
Windows:
```
./gradlew.bat test
```
