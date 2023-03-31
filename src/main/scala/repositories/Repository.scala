package repositories

trait Repository[A]:
  protected var entities: List[A] = List()

  def equal(entityA: Any, entity: Any): Boolean

  def save(entity: A): Unit = entities = entity :: entities

  def remove(entity: A): Unit = entities =
    entities.filter(e => equal(entity, e))

  def update(entity: A): Unit =
    remove(entity)
    save(entity)
