@VPSTesting_GCP
Feature:Test SendFax API for Virtual Payment Service

  @VPSSendFax
  Scenario Outline: User is able to validate the SendFax API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
     | systemDateTime | localDateTime | recordLocator | deployementID |
    And User sets the baseURI and basePath for SendFax request
    Then User sends the API request for SendFax request
    And Store the paymentReferenceNumber and display on console for SendFax request

  Examples:
  |  fileName   |
  |   sendFax   |