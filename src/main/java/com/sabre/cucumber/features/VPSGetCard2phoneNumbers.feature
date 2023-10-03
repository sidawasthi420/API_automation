@VPSTesting_GCP
Feature:Test GETCard 2 Phone Numbers API for Virtual Payment Service

  @VPSGetCard2PhoneNumbers
  Scenario Outline: User is able to validate the GETCard 2 Phone Numbers API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | checkInDate | checkOutDate | recordLocator |
    And User sets the baseURI and basePath for GETCard2phoneNumbers request
    Then User sends the API request for GETCard2phoneNumbers request
    And Store the paymentReferenceNumber and display on console for GETCard2phoneNumbers request

  Examples:
  |       fileName           |
  |   getCard2phoneNumbers   |