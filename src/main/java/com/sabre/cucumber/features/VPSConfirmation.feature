@VPSTesting_GCP
Feature:Test Confirmation API for Virtual Payment Service

  @VPSConfirmation
  Scenario Outline: User is able to validate the Confirmation API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | recordLocator | deployementID |
    And User sets the baseURI and basePath for Confirmation request
    Then User sends the API request for Confirmation request
    And Store the paymentReferenceNumber and display on console for Confirmation request

  Examples:
    |       fileName      |
    |   vpsConfirmation   |