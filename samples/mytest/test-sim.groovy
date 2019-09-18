import org.arl.fjage.RealTimePlatform




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
