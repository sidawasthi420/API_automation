@VPSTesting_GCP
Feature:Test SendFax Hotel Email AgyEmail Non Fax API for Virtual Payment Service

  @VPSSendFaxHotelEmailAgyPhoneNonFax
  Scenario Outline: User is able to validate the SendFax Hotel Email AgyEmail Non Fax API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
      | systemDateTime | checkInDate | checkOutDate | recordLocator | deployementID |
    And User sets the baseURI and basePath for SendFaxHotelEmailAgyPhoneNonFax request
    Then User sends the API request for SendFaxHotelEmailAgyPhoneNonFax request
    And Store the paymentReferenceNumber and display on console for SendFaxHotelEmailAgyPhoneNonFax request

  Examples:
  |             fileName                |
  |   sendFaxHotelEmailAgyPhoneNonFax   |