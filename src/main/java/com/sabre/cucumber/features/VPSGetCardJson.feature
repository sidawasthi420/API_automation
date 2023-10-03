@VPSTesting_GCP
Feature:Test GETCard Json API for Virtual Payment Service

  @VPSGetCardJson
  Scenario Outline: User is able to validate the GETCardJson API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | departureDateTime | arrivalDateTime | recordLocator |
    And User sets the baseURI and basePath for GETCardJson request
    Then User sends the API request for GETCardJson request
    And Store the paymentReferenceNumber and display on console for GETCardJson request

  Examples:
    |        fileName              |
    |       getCardJson            |