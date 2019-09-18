import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.bb.*
import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*
import java.util.Random;

import java.lang.Math.*;




platform = RealTimePlatform

// adopt MISSION 2012 channel model
channel = [ model: AbsorptionSpreadLossChannel ]

// run the simulation forever
simulate {
  AbsorptionSpreadLossChannel.nodes.each { addr ->
    node "$addr", location: AbsorptionSpreadLossChannel.nodeLocation[addr], remote: (1100+addr),
         shell: (addr==21)?[true,5121]:(5100+addr), stack: "$home/etc/initrc-stack.groovy"
  }
}
