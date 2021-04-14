# NEU-Eat
NEU-Eat is a web application that provide service to customers, drivers and restaurants in a food delivery system.
Our purpose is to provide the biggest flexibility and functionality to the users.
The app is deployed on Heroku: https://neu-eat.herokuapp.com/

The project follows MVC design pattern. We used Spring Boot + MongoDB to support RESTful APIS, and built a user-friendly frontend using React.

In order to deploy the app to Heroku, first we added maven-frontend-plugin to package our frontend and backend together. Then, we created a new cluster in MongoDB Altas to store our database. After that, we edited our "app-running" command in Procfile (for being recognized by Heroku). At last, we generated a new "jar" file,  created a new app in Heroku and linkd our Git repository to the app.


If you want to deploy the app in your computer. You should follow the steps:
1. Clone the repository
2. Start the MongoDB
3. Run the "NEUApplication", then you app will starts running in http://localhost:8080/
