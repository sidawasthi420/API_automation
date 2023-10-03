Feature:Test GETCard API for Virtual Payment Service

  @VPSGenerateTokenNonDev
  Scenario Outline: User is able to generate token using API Response
    Given  User sets the baseURI and basePath for generate token
    When User sends the API request to generate token
    Then Store the token and display on console

    Examples:
      |  code   |   Description  |
      | SUCCESS |   Successful   |