//! Simulation: Simple 3-node network
///////////////////////////////////////////////////////////////////////////////
///
/// To run simulation:
///   bin/unet samples/rt/3-node-network
///
///////////////////////////////////////////////////////////////////////////////

import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*





///////////////////////////////////////////////////////////////////////////////
// display documentation

println '''
3-node network
--------------

Nodes: 1, 2, 3

To connect to node 2 or node 3 via telnet:
  telnet localhost 5102
  telnet localhost 5103

To connect to nodes 1, 2 or 3 via unet sh:
  bin/unet sh localhost 1101
  bin/unet sh localhost 1102
  bin/unet sh localhost 1103

Connected to node 1...
Press ^D to exit
'''

///////////////////////////////////////////////////////////////////////////////
// simulator configuration

trace.warmup = 2.minutes             // collect statistics after a while


platform = RealTimePlatform   // use real-time mode


// adopt MISSION 2012 channel model
channel = [ model: AbsorptionSpreadLossChannel ]

def loadRange = [0.1, 1.5, 0.1]    


subscribe PHYSICAL;

//for (def load = loadRange[0]; load <= loadRange[1]; load += loadRange[2]) {
// run the simulation forever


//for (def load = loadRange[0]; load <= loadRange[1]; load += loadRange[2]) {
simulate  {
  node '1', remote: 1101, address: 1, location: [ 0.km, 0.km, -15.m], shell: true, stack:  {container ->
   container.shell.addInitrc "${script.parent}/fshrc.groovy"
}
  //node '2', remote: 1102, address: 2, location: [ 3.km, 0.km, -15.m], shell: 5102, stack: "$home/etc/initrc-stack"
 node '2', remote: 1103, address: 3, location: [2.km, 0.km, -15.m], shell: 5103, stack: "$home/etc/initrc-stack"



 

}



float loss = trace.txCount ? 100*trace.dropCount/trace.txCount : 0
 println sprintf('%6d\t\t%6d\t\t%5.1f\t\t%7.3f\t\t%7.3f',[trace.txCount, trace.rxCount, loss, trace.offeredLoad, trace.throughput])


