# Chat Server

## Overview
This project is a web server built with Kotlin using the Ktor framework. It is designed to support a CLI messaging application, with potential future enhancements to include more functionalities, such as 2p2 messaging and file transfer. 

## Current Status
- The project is currently under development.
- The server component is being implemented to handle messaging functionalities.

## Technologies Used
- **Kotlin**: The primary programming language used.
- **Ktor**: A framework used for building asynchronous servers and clients in connected systems.

## Planned Features
- **CLI Interface**: A command-line interface for the server to allow admin management and monitoring.
- **Messaging Capabilities**: Core functionality for the CLI messaging app.
- **Extendable Architecture**: Future functionalities can be added, such as file transfers and additional communication features.

## Getting Started
To get started with this project, follow the steps below:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/physine/ChatterBoxServer.git
   ```
   
***

TODO:
- [ ] rely on the .env to tell ktor where to find the db. (better for prod)
- [ ] make a db with an orm (may require docker to work on any pc)
- [ ] set up the websockets
- [ ] remove jwt from Response as not a responses has a jwt field
- [x] finish the user controller (JWTPrincipal where needed), service, and repo