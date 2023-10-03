@VPSTesting_GCP
Feature:Test GETCard API for Virtual Payment Service

  @VPSGetCard
  Scenario Outline: User is able to validate the GETCard API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | checkInDate | checkOutDate | recordLocator |
    And User sets the baseURI and basePath for GETCard request
    Then User sends the API request for GETCard request
    And Store the paymentReferenceNumber and display on console for GET Card request

  Examples:
    |       fileName           |
    |       test            |