//! Simulation: MISSION 2012 network
///////////////////////////////////////////////////////////////////////////////
///
/// To run simulation:
///   bin/unet samples/mission2012/mission2012-sim
///
///////////////////////////////////////////////////////////////////////////////

import org.arl.fjage.RealTimePlatform

///////////////////////////////////////////////////////////////////////////////
// display documentation

println '''
MISSION 2012 network
--------------------

Nodes: 21, 22, 27, 28, 29

To connect to nodes via telnet shell:
22: telnet localhost 5122
27: telnet localhost 5127
28: telnet localhost 5128
29: telnet localhost 5129

Or to connect to nodes via unetsh:
21: bin/unet sh localhost 1121
22: bin/unet sh localhost 1122
27: bin/unet sh localhost 1127
28: bin/unet sh localhost 1128
29: bin/unet sh localhost 1129

Connected to 21...
Press ^D to exit
'''

///////////////////////////////////////////////////////////////////////////////
// simulator configuration

platform = RealTimePlatform

// adopt MISSION 2012 channel model
channel = [ model: AbsorptionSpreadLossChannel, 
bandwidth:            4096.Hz,
  spreading:            1.5,
  temperature:          15.C,
  salinity:             35.ppt,
  noiseLevel:           60.dB,
  waterDepth:           20.m,
  carrierFrequency:     2.Hz,]

// run the simulation forever
simulate {
  AbsorptionSpreadLossChannel.nodes.each { addr ->
    node "$addr", location: AbsorptionSpreadLossChannel.nodeLocation[addr], remote: (1100+addr),
         shell: (addr==21)?[true,5121]:(5100+addr),stack: { container ->
    container.shell.addInitrc "${script.parent}/fshrc.groovy"
  }
}
}