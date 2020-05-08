# Testing_Security_Devolpment_Enterprise_Systems PG5100 2020 - A Gacha Lootbox Game! <(''<)

### Exam

* The Requirments through R1 to R5 has been completed.
* Extra features added: Search though list of available items, User profile page and the ability for the user to change his/her own password. 

### Program Features

* Log In/Sign Out.
* List of available items. 
* Buy Lootboxes.
* Sell/Mill Items.
* Search through the Item list on the home page.
* Show duplicated item on the list and then Sell/Mill item.
* User Profile Page with user information.
* User can change his/her own password. 

### Usage

 * Running Tests from the root: ``mvn install``.
 * Running without tests: ``mvn install -DskipTests``
 * Verify the Project through ``mvn verify``
 * To run this project, run the ``LocalApplicationRunner`` in the frontend module.

### Bugs

* Opening a Lootbox might throw this exception on the UI: 
``Unable to find matching navigation case with from-view-id '/collection.xhtml' for action '#{itemController.buyLootBox(userInfoController.userName)}' with outcome '19500'``

### Test

* Tests: Selenium through Chromedriver and JJunit. 
* Backend Test Coverage: __94%__
* Frontend Test Coverage: __89%__
* Total test Coverae: __92%__

![Image of Application's Test Coverage](https://i.imgur.com/v2GdAoJ.png)

### Notes

* The Copy requierment I am not sure I did as asked on the requirment, so I did what I thought was right.

## Disclaimers 

* Codes used from other repositories are credited on each code class with direct link to said codes.they are from my lecturer Andrea Arcuri's repository 
©[Testing_Security_Development_Enterprise_System](https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/report/pom.xml)
* Items used in this repositories are Pokemons owned by Nintendo and Gamefreak. ©[Pokemon Legal](https://www.pokemon.com/us/legal/)
