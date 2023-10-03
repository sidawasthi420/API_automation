@VPSTesting_GCP
Feature:Test Update Modify Test API for Virtual Payment Service

  @VPSUpdateModifyTest
  Scenario Outline: User is able to validate the Update Modify Test API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | checkInDate | checkOutDate | recordLocator | deployementID |
    And User sets the baseURI and basePath for Update Modify Test request
    Then User sends the API request for Update Modify Test request
    And Store the paymentReferenceNumber and display on console for Update Modify Test request

  Examples:
    |       fileName       |
    |   updateModifyTest   |