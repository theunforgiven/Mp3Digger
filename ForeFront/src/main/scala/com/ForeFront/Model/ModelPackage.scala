import org.fusesource.scalate.support.TemplatePackage
import org.fusesource.scalate.{Binding, TemplateSource}


class ModelPackage extends TemplatePackage {
  def header(source: TemplateSource, bindings: List[Binding]): String = """
    import com.ForeFront.Model._

  """
}