package crypto.org.crypto.Model;

import java.util.List;

/**
 * Created by Qunfo on 15-Nov-17.
 * Id : 0
 * Name : BitCoin joris
 * Valuta : {"Id":1,"Name":"BitCoin","ShortName":"BTC","CurrentPrice":12.5}
 * graphData : [{"Id":0,"Low":6562.85009765625,"High":6622.93994140625,"Open":6564.6201171875,"Close":6588.6201171875,"TimeStamp":1510639200},{"Id":0,"Low":6568.56005859375,"High":6599.8798828125,"Open":6588.240234375,"Close":6590.41015625,"TimeStamp":1510642800},{"Id":0,"Low":6480.5400390625,"High":6593.6201171875,"Open":6590.41015625,"Close":6492.43017578125,"TimeStamp":1510646400},{"Id":0,"Low":6490.58984375,"High":6564.8701171875,"Open":6492.35009765625,"Close":6562.2900390625,"TimeStamp":1510650000},{"Id":0,"Low":6557.41015625,"High":6618.68994140625,"Open":6566.580078125,"Close":6597,"TimeStamp":1510653600},{"Id":0,"Low":6482.22021484375,"High":6596.990234375,"Open":6596.990234375,"Close":6488.27978515625,"TimeStamp":1510657200},{"Id":0,"Low":6419.18017578125,"High":6514.4501953125,"Open":6488.27978515625,"Close":6500.5400390625,"TimeStamp":1510660800},{"Id":0,"Low":6450.6298828125,"High":6501.10986328125,"Open":6499.6201171875,"Close":6472.1201171875,"TimeStamp":1510664400},{"Id":0,"Low":6469.56982421875,"High":6549.06005859375,"Open":6470.85009765625,"Close":6549.06005859375,"TimeStamp":1510668000},{"Id":0,"Low":6504.47021484375,"High":6562.22021484375,"Open":6549.10009765625,"Close":6528.9599609375,"TimeStamp":1510671600},{"Id":0,"Low":6487.35009765625,"High":6568.4599609375,"Open":6528.31982421875,"Close":6561.2900390625,"TimeStamp":1510675200},{"Id":0,"Low":6538.89990234375,"High":6592.0498046875,"Open":6561.080078125,"Close":6541,"TimeStamp":1510678800},{"Id":0,"Low":6512.72021484375,"High":6581.10986328125,"Open":6542.9599609375,"Close":6522.08984375,"TimeStamp":1510682400},{"Id":0,"Low":6520.56982421875,"High":6608.9599609375,"Open":6521.83984375,"Close":6608.9501953125,"TimeStamp":1510686000},{"Id":0,"Low":6592.4599609375,"High":6639.7900390625,"Open":6609.47998046875,"Close":6622.6298828125,"TimeStamp":1510689600},{"Id":0,"Low":6575.240234375,"High":6624.72998046875,"Open":6624.259765625,"Close":6595.56005859375,"TimeStamp":1510693200},{"Id":0,"Low":6560.72021484375,"High":6620.7998046875,"Open":6595.7998046875,"Close":6573.81982421875,"TimeStamp":1510696800},{"Id":0,"Low":6543.39013671875,"High":6599.77978515625,"Open":6574.75,"Close":6597.06005859375,"TimeStamp":1510700400},{"Id":0,"Low":6596.93994140625,"High":6755.330078125,"Open":6597.06005859375,"Close":6736.93994140625,"TimeStamp":1510704000},{"Id":0,"Low":6735.580078125,"High":6786.18017578125,"Open":6736.89990234375,"Close":6775.85009765625,"TimeStamp":1510707600},{"Id":0,"Low":6766.31982421875,"High":6908.7900390625,"Open":6775.85009765625,"Close":6888.5498046875,"TimeStamp":1510711200},{"Id":0,"Low":6815.75,"High":6888.5,"Open":6887.990234375,"Close":6863.490234375,"TimeStamp":1510714800},{"Id":0,"Low":6792.419921875,"High":6864.97998046875,"Open":6863.490234375,"Close":6837.72021484375,"TimeStamp":1510718400},{"Id":0,"Low":6823.16015625,"High":6867.72998046875,"Open":6837.60986328125,"Close":6826.5400390625,"TimeStamp":1510722000},{"Id":0,"Low":6821.10009765625,"High":6864.68017578125,"Open":6826.5400390625,"Close":6858.6201171875,"TimeStamp":1510725600},{"Id":0,"Low":6857.47998046875,"High":6940.35009765625,"Open":6858.27978515625,"Close":6906.5498046875,"TimeStamp":1510729200},{"Id":0,"Low":6898.5498046875,"High":6925.77978515625,"Open":6906.5498046875,"Close":6922.14013671875,"TimeStamp":1510732800},{"Id":0,"Low":6893.66015625,"High":6954.990234375,"Open":6922.14013671875,"Close":6893.66015625,"TimeStamp":1510736400},{"Id":0,"Low":6868.2900390625,"High":6941.5498046875,"Open":6895.31005859375,"Close":6941.5498046875,"TimeStamp":1510740000},{"Id":0,"Low":6940.68994140625,"High":7060,"Open":6941.5400390625,"Close":7057.60986328125,"TimeStamp":1510743600},{"Id":0,"Low":7057.2099609375,"High":7074.43994140625,"Open":7059.39013671875,"Close":7099.43994140625,"TimeStamp":1510747200}]
 */
public class Graph {
    private int Id;
    private String Name;
    private Valuta Valuta;
    private List<GraphData> graphData;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Valuta getValuta() {
        return Valuta;
    }

    public void setValuta(Valuta Valuta) {
        this.Valuta = Valuta;
    }

    public List<GraphData> getGraphData() {
        return graphData;
    }

    public void setGraphData(List<GraphData> graphData) {
        this.graphData = graphData;
    }

}
