# ECSE 321 Team 12 (Reindeer Games, Inc 🦌)

## The Project
An online game store (full stack web application).\
Project scope: requirements elicitation and documentation, persistence layer, database, backend, frontend.  
Our figma project for initial designs of the frontend is [available here](https://www.figma.com/design/gzwJwFxoi9lAcxO2Wo3e3c/Reindeer-Games?node-id=0-1&t=WGEjltFWPSBwlwhZ-1).

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
| Name             | Team Roles       | Hours                 |Individual Effort |
| ---------------- | ---------------- | --------------------- |----------------- |
| James Madden     | Tech Lead        |  18                   |<ul><li>Work on detailed use case specification</li><li>Work on class diagram</li><li>Create persistence layer using Hibernate</li><li>Write tests for persistence layer</li><li>Set up build system</li></ul>   |
| Sophia Li        | Project Manager  |  18                   |<ul><li>Update wiki</li><li>Work on detailed use case specification</li><li>Work and adjust class diagram</li><li>Write tests for persistence layer</li></ul> |
| Amy Ding         | UI/UX Designer   |  18                   |<ul><li>Update wiki</li><li>Work on use case diagrams</li><li>Work on detailed use case specification</li><li>Write tests for persistence layer</li><li>Work on class diagram</li></ul> |
| Julien Heng      | Developer        |  13                   | <ul><li>Update wiki</li><li>Work on detailed use case specification</li><li>Work and adjust class diagram</li><li>Write tests for persistence layer</li></ul>|
| Kennedy Olsen    | Business Analyst |  18                   |<ul><li>Update wiki</li><li>Work on detailed use case specification</li><li>Work and adjust class diagram</li><li>Write tests for persistence layer</li></ul> |
| Carmin Vidé      | Scrum Master     |  18                   |<ul><li>Work on detailed use case specification</li><li>Work on use case diagrams</li><li>Work on class diagram</li><li>Write tests for persistence layer</li></ul> |

### Deliverable 2
| Name             | Team Roles       | Hours | Individual Effort |
| ---------------- | ---------------- | ----- | ----------------- |
| James Madden     | Tech Lead        |  27   | <ul><li> Implemented order related use cases</li><li> Implemented authentication</li><li> Implemented token-based authorization</li><li>Implemented card payment service and tests</li></ul>|
| Sophia Li        | Project Manager  |  25   | <ul><li> Implemented cart related use cases</li><li>Implemented test coverage</li></ul>   |
| Amy Ding         | UI/UX Designer   |   24  | <ul><li> Implemented employee related use cases</li><li>Updated Wiki for Software Quality Assurance Plan and Report</li><li>Took meeting minutes</li><li>Implemented manager related use cases</li><li>Add various unit tests</li></ul>|
| Julien Heng      | Developer        |  25   | <ul><li> Implemented game related use cases and tests</li><li> Implemented discounts on orders and tests</li><li> Implemented wishlist related business logic and tests</li><li> Updated API endpoints documentation</li></ul> |
| Kennedy Olsen    | Business Analyst |  19.5 | <ul><li> Implemented review related use cases</li><li> Updated Wiki for Quality Assurance Report</li></ul> |
| Carmin Vidé | Scrum Master     |   19  | <ul><li> Implemented customer and comment related use cases</li><li> Updated Wiki for Quality Assurance Report</li></ul>|

### Deliverable 3
| Name             | Team Roles       | Hours | Individual Effort |
| ---------------- | ---------------- | ----- | ----------------- |
| James Madden     | Tech Lead        |  27   | <ul><li> Designed and implemented home page</li><li> Designed and implemented navbar</li><li> Designed and implemented individual game page</li><li>Designed and implemented search results page</li></ul>|
| Sophia Li        | Project Manager  |  25   | <ul><li> Designed and implemented wishlist page</li><li>Designed checkout page</li></ul>   |
| Amy Ding         | UI/UX Designer   |   24  | <ul><li> Designed and implemented profile page</li><li>Designed and implemented orders page</li><li>Implement checkout page</li><li>Updated wiki and README.</li></ul>|
| Julien Heng      | Developer        |  25   | <ul><li>Designed and implemented login page</li><li>Implement checkout page</li><li>Updated backend requests for password change endpoint</li><li>Add images to database</li></ul> |
| Kennedy Olsen    | Business Analyst |  19.5 | <ul><li>Designed and implemented cart page</li></ul> |
| Carmin Vidé | Scrum Master     |   19  | <ul><li>Designed and implemented manager dashboard</li><li>Designed and implemented employee dashboard</li></ul>|


## Contributing
To ensure high quality, well tested code, do the following when contributing to this project:  
1. Clone the project locally.
2. Create a branch: `name/feature`
3. Push your code to that branch.
4. When you're done, run `bash ./gradlew format` (or `./gradlew.bat format` for Windows) for a backend change, or `npm run format` for a frontend change.
5. Create a pull request and assign another team member to review your code.
6. Once they've reviewed your code, they'll merge it into main!

## Run the Project
For the user authentication, you must create a `.env` file in the root of the project, and enter in `JWT_SECRET="<...>"`. Replace with any secret you'd like.  

Start the frontend:
```
cd frontend
npm run dev
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
This will run both the unit and integration tests.

Test the backend:\
Unix:
```
bash ./gradlew test
```
Windows:
```
./gradlew.bat test
```

## Deployment
Please run the code locally. This project has not been deployed yet.
