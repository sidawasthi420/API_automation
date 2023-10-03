@VPSTesting_GCP
Feature:Test GetSendFaxHistory API for Virtual Payment Service

  @VPSGetSendFaxHistory
  Scenario Outline: User is able to validate the GETSendFaxHistory API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | recordLocator | deployementID |
    And User sets the baseURI and basePath for GETSendFaxHistory request
    Then User sends the API request for GETSendFaxHistory request
    And Store the paymentReferenceNumber and display on console for GETSendFaxHistory request

  Examples:
    |     fileName          |
    |   getSendFaxHistory   |