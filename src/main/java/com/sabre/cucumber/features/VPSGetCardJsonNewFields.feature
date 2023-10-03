@VPSTesting_GCP
Feature:Test GETCard Json New Fields API for Virtual Payment Service

  @VPSGetCardJsonNewFields
  Scenario Outline: User is able to validate the GETCardJsonNewFields API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
      | systemDateTime | localDateTime | checkInDate | checkOutDate | recordLocator |
    And User sets the baseURI and basePath for GETCardJsonNewFields request
    Then User sends the API request for GETCardJsonNewFields request
    And Store the paymentReferenceNumber and display on console for GET Card JsonNewFields request

  Examples:
    |               fileName                |
    |       getCardJsonNewFields            |