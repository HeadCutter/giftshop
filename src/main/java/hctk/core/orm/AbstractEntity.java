package hctk.core.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hctk.core.ioc.Container;
import hctk.core.orm.annotations.EntityProp;
import hctk.core.orm.annotations.EntityPropExt;

public class AbstractEntity implements Cloneable{

    protected int id;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
    
    public String toString() {
        return this.getClass().getName() + this.getPropsData(); 
    }
    
    AbstractEntity newInstance() {
        try {
            return (AbstractEntity)clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AbstractEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    List<PropDef> getProps() {
        List<PropDef> list = new LinkedList<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation an : annotations) {
                if ((an instanceof EntityProp) || (an instanceof EntityPropExt)) {
                    String type = field.getType().toString();
                    if(field.getType() == String.class) {
                        type = "string";
                    }
                    PropDef def;
                    if (an instanceof EntityPropExt)
                    {
                        def = new PropDefExt(field.getName(), type, ((EntityPropExt) an).repo());
                    } else {
                        def = new PropDef(field.getName(), type);
                    }
                    list.add(def);
                }
            }
        }
        return list;
    }

    List<String> getPropsData() {
        return getPropsData(getProps());
    }

    void setPropsData(List<String> data) {
        setPropsData(getProps(), data);
    }

    List<String> getPropsData(List<PropDef> props) {
        List<String> list = new ArrayList<>();
        for (PropDef prop : props) {
            try {
                Field field = this.getClass().getDeclaredField(prop.getName());
                boolean removeAccess = false;
                if (!field.canAccess(this)) {
                    removeAccess = true;
                    field.setAccessible(true);
                }
                Object res;
                if(prop instanceof PropDefExt) {
                    res = ((AbstractEntity)field.get(this)).getId();
                } else {
                    res = field.get(this);
                }
                if (removeAccess) {
                    field.setAccessible(true);
                }
                if (res != null) {
                    list.add(res.toString());
                } else {
                    list.add(null);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(AbstractEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    void setPropsData(List<PropDef> props, List<String> data) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < props.size(); ++i) {
            PropDef prop = props.get(i);
            try {
                Field field = this.getClass().getDeclaredField(prop.getName());
                boolean removeAccess = false;
                if (!field.canAccess(this)) {
                    removeAccess = true;
                    field.setAccessible(true);
                }
                String val = data.get(i);
                Object res;
                if(prop instanceof PropDefExt) {
                    res = ((AbstractRepo)(Container.getInstance().resolve("repo." + ((PropDefExt) prop).getRepo()))).get(Integer.valueOf(val));
                } else {
                    res = (Object) (switch (prop.getType()) {
                        case "int" -> Integer.valueOf(val);
                        case "double" -> Double.valueOf(val);
                        default -> val;
                    });
                }
                field.set(this, res);
                if (removeAccess) {
                    field.setAccessible(true);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(AbstractEntity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class PropDef {

    private final String name;
    private final String type;

    public PropDef(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + ": " + type;
    }
}

class PropDefExt extends PropDef {
    private final String repo;
    public PropDefExt(String name, String type, String repo) {
        super(name, type);
        this.repo = repo;
    }

    public String getRepo() {
        return repo;
    }
}
