@VPSTesting_GCP
Feature:Test GETCard Json Changes MultiFax API for Virtual Payment Service

  @VPSGetCardJsonChangesMultiFax
  Scenario Outline: User is able to validate the GETCardJsonChangesMultiFax API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
      | systemDateTime | localDateTime | departureDateTime | arrivalDateTime | recordLocator |
    And User sets the baseURI and basePath for GETCardJsonChangesMultiFax request
    Then User sends the API request for GETCardJsonChangesMultiFax request
    And Store the paymentReferenceNumber and display on console for GET Card JsonChangesMultiFax request

  Examples:
    |               fileName                      |
    |       getCardJsonChangesMultiFax            |