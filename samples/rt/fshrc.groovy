import org.arl.unet.*
import org.arl.unet.phy.*

subscribe phy

//def phy = agentForService PHYSICAL
//def node = agentForService NODE_INFO

def n = [21, 22, 27, 28, 29]

for (int i=0;i<5;i++){
	if(node.address==21){
              phy << new TxFrameReq(to:n[i], type: DATA, data: dat.bytes)
      }
  }