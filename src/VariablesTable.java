import java.util.ArrayList;
import java.util.List;

public class VariablesTable {

    public static class  tVariable {
        String type;        //int
        String title;       //sayHello ()
        String  value;      // null
        String parent;      // указывается title родителя. Если нет - null (аналог области видимости вроде как)

        List<tVariable> _listOfArguments = new ArrayList<>();   //list of arguments (у этих детей parent будет "sayHello"

        public tVariable (String type, String title, String value, String parent, List<tVariable> _listOfArguments) {
            this.title = title;
            this.type = type;
            this.value = value;
            this._listOfArguments = _listOfArguments;
        }

        public tVariable(String type, String title) {
            this.title = title;
            this.type = type;
        }
    }

    List<tVariable> _listOfVariables = new ArrayList<>(); //фактический список всех групп переменных/функций

    public List<tVariable> get_listOfVariables() {
        return _listOfVariables;
    }

    public boolean addVariable(tVariable variable) {

        for (int i = 0; i < this._listOfVariables.size(); i++) {
            tVariable v = this._listOfVariables.get(i);

            System.out.println(v.type + ", " + variable.type);
            System.out.println(v.value + ", " + variable.value);
            System.out.println(v.title + ", " + variable.title);
            System.out.println(v._listOfArguments + ", " + variable._listOfArguments);
            System.out.println(v.parent + ", " + variable.parent);
            System.out.println("##################");

            if (v == variable) {
                System.out.println("FUUUUUUUUUUUUUCK");
            }

//            if ((v.type.equals(variable.type)) &&
//                    (v.value.equals(variable.value)) &&
//                    (v.title.equals(variable.title)) &&
//                    (v._listOfArguments.equals(variable._listOfArguments)) &&
//                    (v.parent.equals(variable.parent))) {
//                System.out.println("Variable already exists");
//                return false;
//            }
        }

        System.out.println("ADDITING VALUE");
        this._listOfVariables.add(variable);
        return true;
    }
}
