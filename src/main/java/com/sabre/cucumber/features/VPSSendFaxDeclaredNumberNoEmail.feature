@VPSTesting_GCP
Feature:Test SendFax Declared Number No EMail API for Virtual Payment Service

  @VPSSendFaxDeclaredNumberNoEmail
  Scenario Outline: User is able to validate the SendFax Declared Number No EMail API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
      | systemDateTime | checkInDate | checkOutDate | recordLocator | deployementID |
    And User sets the baseURI and basePath for SendFaxDeclaredNumberNoEmail request
    Then User sends the API request for SendFaxDeclaredNumberNoEmail request
    And Store the paymentReferenceNumber and display on console for SendFaxDeclaredNumberNoEmail request

  Examples:
  |             fileName             |
  |   sendFaxDeclaredNumberNoEmail   |