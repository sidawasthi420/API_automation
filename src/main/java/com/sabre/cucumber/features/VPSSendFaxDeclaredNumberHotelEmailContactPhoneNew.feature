@VPSTesting_GCP
Feature:Test SendFax Declared Number NeitherHotelEmail/ContactPhoneNew API for Virtual Payment Service

  @VPSSendFaxDeclaredNumberHotelEmail_ContactPhoneNew
  Scenario Outline: User is able to validate the SendFax Declared Number NeitherHotelEmail/ContactPhoneNew API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in millisecond datetime format for "<fileName>"
      | systemDateTime | checkInDate | checkOutDate | recordLocator | deployementID |
    And User sets the baseURI and basePath for SendFaxDeclaredNumberHotelEmail_ContactPhoneNew request
    Then User sends the API request for SendFaxDeclaredNumberHotelEmail_ContactPhoneNew request
    And Store the paymentReferenceNumber and display on console for SendFaxDeclaredNumberHotelEmail_ContactPhoneNew request

  Examples:
  |                    fileName                        |
  |   sendFaxDeclaredNumberHotelEmailContactPhoneNew   |