package repositories

import models.Account

object AccountRepository extends Repository[Account]:

  override def equal(entityA: Any, entityB: Any): Boolean =
    val (entityAInstance, entityBInstance) =
      (entityA.asInstanceOf[Account], entityB.asInstanceOf[Account])
    entityAInstance.id == entityBInstance.id

  def findById(id: String): Option[Account] = entities.find(_.id == id)

  def findAll(): List[Account] = entities
