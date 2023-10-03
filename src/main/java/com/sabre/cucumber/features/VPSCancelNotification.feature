@VPSTesting_GCP
Feature:Test Cancel Notification API for Virtual Payment Service

  @VPSCancelNotification
  Scenario Outline: User is able to validate the CancelNotification API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console
    Then Store the deployementID from GETCard request
    Then Update body parameter of request in yyyy-MM-ddTHH:mm:ssZ format for "<fileNameSub>"
      | systemDateTime | localDateTime | recordLocator | deployementID |
    And User sets the baseURI and basePath for Confirmation request
    Then User sends the API request for Confirmation request
    And Store the paymentReferenceNumber and display on console for Confirmation request
    Then Update the body parameter of CancelNotification request in yyyy-MM-ddTHH:mm:ssZ format for "<fileNameMain>"
      | systemDateTime | localDateTime | recordLocator | paymentReferenceNumber | deployementID |
    And User sets the baseURI and basePath for CancelNotification request
    Then User sends the API request for CancelNotification request
    And Store the paymentReferenceNumber and display on console for CancelNotification request

  Examples:
    |    fileNameSub      |     fileNameMain       |
    |   vpsConfirmation   |  vpsCancelNotification |