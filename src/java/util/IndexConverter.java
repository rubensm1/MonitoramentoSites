package util;

import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author luizalvino
 * @update jeandobre
 */
@FacesConverter(value = "indexConverter")
public class IndexConverter implements Converter {

    private transient int index;

    @Override
    public Object getAsObject(final FacesContext facesContext, final UIComponent uicomp, final String value) {
        final List<Object> items = new ArrayList<Object>();
        final List<UIComponent> uicompList = uicomp.getChildren();
        for (UIComponent comp : uicompList) {
            if (comp instanceof UISelectItems) {
                items.addAll((List<Object>) ((UISelectItems) comp).getValue());
            }
        }
        return "-1".equals(value) ? null : items.get(Integer.valueOf(value));
    }

    @Override
    public String getAsString(final FacesContext facesContext, final UIComponent uicomp, final Object entity) {
        return entity == null ? "-1" : String.valueOf(index++);
    }
}
