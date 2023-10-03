@VPSTesting_GCP
Feature:Test SendFaxHotelFax API for Virtual Payment Service

  @VPSSendFaxHotelFax
  Scenario Outline: User is able to validate the SendFaxHotelFax API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
     | systemDateTime | localDateTime | recordLocator | deployementID |
    And User sets the baseURI and basePath for SendFaxHotelFax request
    Then User sends the API request for SendFaxHotelFax request
    And Store the paymentReferenceNumber and display on console for SendFaxHotelFax request

  Examples:
  |       fileName      |
  |   sendFaxHotelFax   |