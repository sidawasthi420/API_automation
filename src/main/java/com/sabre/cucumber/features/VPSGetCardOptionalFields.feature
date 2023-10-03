@VPSTesting_GCP
Feature:Test GETCard Optional Fields API for Virtual Payment Service

  @VPSGetCardOptionalFields
  Scenario Outline: User is able to validate the GETCard Optional Fields API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileName>"
      | systemDateTime | localDateTime | checkInDate | checkOutDate | recordLocator |
    And User sets the baseURI and basePath for GETCard Optional Fields request
    Then User sends the API request for GETCard Optional Fields request
    And Store the paymentReferenceNumber and display on console for GETCard Optional Fields request

  Examples:
    |        fileName           |
    |   getCardOptionalFields   |