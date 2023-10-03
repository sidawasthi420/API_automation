@VPSTesting_GCP
Feature:Test Update Modify API for Virtual Payment Service

  @VPSUpdateModify
  Scenario Outline: User is able to validate the Update Modify API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | checkInDate | checkOutDate | recordLocator | deployementID |
    And User sets the baseURI and basePath for Update Modify request
    Then User sends the API request for Update Modify request
    And Store the paymentReferenceNumber and display on console for Update Modify request

  Examples:
    |     fileName     |
    |   updateModify   |