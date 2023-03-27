package utils

import models.BalanceType

extension (s: String)
  def convertToBalanceType: BalanceType = s match 
    case "1" | "2" => BalanceType.FOOD
    case "3" | "4" => BalanceType.MEAL
    case _ => BalanceType.CASH


