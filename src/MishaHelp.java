import java.util.HashMap;
import java.util.Map;

public class MishaHelp {
    void demo() {
        // я хочу хранить переменные такого типа
        // int a = 15; string b = "16";

        // ############## ВАРИАНТ 1 ##############
        // аналогичная запись
        // {
        //    "a": "15",
        //    "b": "16"
        // }
        // пояснение - я храню только значние переменной.
        // в таком случае подходит обынчнй map
        Map<String, String> variables1 = new HashMap<String, String>();

        // сохранить переменную
        variables1.put("a", "15");
        variables1.put("b", "16");
        // взять переменную
        variables1.get("a");
        variables1.get("b");
        // МИНУСЫ - я никак не могу определить ТИП переменной, она всегда стринг (либо через try каждое значение парсить потом...)




        //-------------------------------------------------------------------
        // ############## ВАРИАНТ 2 ##############
        // аналогичная запись
        // {
        //    "a": "type: int, value: 15",
        //    "b": "type: string, value: 15",
        // }
        // можно также использовать мапу
        Map<String, String> variables2 = new HashMap<String, String>();
        // сохранить переменную
        variables2.put("a", "type: int, value: 15");
        variables2.put("b", "type: string, value: 16");
        // взять переменную
        variables2.get("a"); // вернет строку: "type: int, value: 15"
        variables2.get("b"); // вернет строку: "type: string, value: 16"
        // МИНУСЫ - чтобы получить значение type, value - мне нужно писать свою функцию обработчика
        // при желании добавить еще несколько полей для переменной - придется переписывать обработчик




        //-------------------------------------------------------------------
        // ############## ВАРИАНТ 3 ##############
        // аналогичная запись
        // {
        //    "a": {
        //          value: "15"
        //          type: "int"
        //     },
        //
        //    "b": {
        //          value: "16"
        //          type: "string"
        //     }
        // }

        HashMap<String, HashMap<String, String>> variables3 = new HashMap<String, HashMap<String,String>>();
        HashMap<String, String> innerMap = new HashMap<String, String>();

        // запись переменных
        innerMap.put("type", "int");
        innerMap.put("value", "15");
        variables3.put("a", innerMap);

        innerMap.put("type", "string");
        innerMap.put("value", "16");
        variables3.put("a", innerMap);

        // взять переменную
        String a_value = ((HashMap<String, String>)variables3.get("a")).get("value").toString();
        System.out.println(a_value); // вернет 15

        String b_type = ((HashMap<String, String>)variables3.get("b")).get("type").toString();
        System.out.println(b_type); // вернет string

        // МИНУСЫ - чуть больше кода для записи и получения переменной
        // плюсы - масштабируемость


        //-------------------------------------------------------------------
        // ############## ВАРИАНТ 4 ##############
        // аналогичная запись
        // {
        //    "a": {
        //          value: "15"
        //          type: "int"
        //     },
        //
        //    "b": {
        //          value: "16"
        //          type: "string"
        //     }
        // }
        // сохранить переменную
        class VarProperties {
            String type;
            String value;
        }
        HashMap<String, VarProperties> variables4 = new HashMap<String, VarProperties>();

        // запись переменной
        VarProperties prop = new VarProperties();
        prop.type = "int";
        prop.value = "15";
        variables4.put("a", prop);

        VarProperties prop2 = new VarProperties();
        prop2.type = "string";
        prop2.value = "16";
        variables4.put("b", prop2);

        // получение переменной
        VarProperties var = variables4.get("a");
        System.out.println(var.type);                   // вернет int
        System.out.println(variables4.get("b").value);  // вернет 16
    }
}
