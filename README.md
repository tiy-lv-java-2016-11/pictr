# Pictr

## Description
We are going to make a very simple picture upload system.  We will also be using an external login system so we don't have to hash passwords.

## Requirements
* Create a spring project with the normal web modules on Spring Initializr
* Create a `User` entity
	* Username
	* Token
* Create a `Photo` entity
	* File name
	* Original file name
	* Caption
	* User
	* Date added
* Don't forget the repositories
* Using the same method login using the external service
	* Login button on the home page to take you through the process
	* Once login confirmed add the username to the session
	* If the user doesn't exist add them to the database
* Allow the user to add a new photo with a caption
* On the home page (login not needed) show the last 10 uploaded photos

## Hard Mode
* Pick a different site that allows single sign on and figure out how to make the process work
* Add an alternate button on the homepage to login for the new service

## Resources
* [Github Repo](https://github.com/tiy-lv-java-2016-11/pictr)
