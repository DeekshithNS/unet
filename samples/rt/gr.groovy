import org.arl.fjage.*
import org.arl.unet.*
import org.arl.unet.phy.*
import org.arl.unet.bb.*
import org.arl.unet.sim.*
import org.arl.unet.sim.channels.*
import static org.arl.unet.Services.*
import static org.arl.unet.phy.Physical.*
import java.util.Random;

import java.lang.Math.*

channel = [
  model: SurfaceDuctLossChannel,
  bandwidth:            4096.Hz,
  spreading:            1.5,
  temperature:          15.C,
  salinity:             35.ppt,
  noiseLevel:           60.dB,
  waterDepth:           20.m,
  carrierFrequency:     2.Hz,
]




for (int f=1;f<10;f++){


simulate{

node '1', remote: 1103, address: 3, location: [0, 0, 0], shell: 5103, stack: "$home/etc/initrc-stack"

node '2', remote: 1103, address: 3, location: [10+(f*10), 0, 0], shell: 5103, stack: "$home/etc/initrc-stack"


}






}