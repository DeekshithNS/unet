import numpy as np
from mpl_toolkits.mplot3d import Axes3D  
import matplotlib as mpl
# Axes3D import has side effects, it enables using projection='3d' in add_subplot
import matplotlib.pyplot as plt
import random


from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
from matplotlib import cm
from matplotlib.ticker import LinearLocator, FormatStrFormatter
import numpy as np


from numpy import empty
freq = [0]*10
bb = [0]*10
ab = [0]*10
sd = [0]*10
sr = [0]*10
#z = empty([200])

i=0
j=0
k=0
q=0



f = open("bb.txt", "r")
for l in f:
	w = l.split(",")
	freq[i]=float(w[0])
	bb[i]=float(w[2])
	#z[i]=w[2]
	i=i+1;

f = open("ab.txt", "r")
for l in f:
	w = l.split(",")
	#distance[i]=float(w[1])
	ab[j]=float(w[2])
	#z[i]=w[2]
	j=j+1;
f = open("sd.txt", "r")
for l in f:
	w = l.split(",")
	#distance[i]=float(w[1])
	sd[k]=float(w[2])
	#z[i]=w[2]
	k=k+1;
f = open("sr.txt", "r")
for l in f:
	w = l.split(",")
	#distance[i]=float(w[1])
	sr[q]=float(w[2])
	#z[i]=w[2]
	q=q+1;





plt.plot(freq,bb, label='Bottom Bounce')
plt.plot(freq,ab, label='absorption loss')
plt.plot(freq,sr, label='surface reflection')
plt.plot(freq,sd, label='surface duct')

plt.xlabel('frequency(Hz)')
plt.ylabel('Attenuation (dB)')

plt.title('Acoustic Transmission Loss Models')

plt.legend()

plt.show()























#X_grid, Y_grid = np.meshgrid(x,y)
#Z_grid = X_grid**2 + Y_grid**2

#plt.contour(X_grid, Y_grid, Z_grid)  # Works fine


#fig = plt.figure()
#ax = fig.add_subplot(111, projection='3d')


#ax.plot_surface(x, y, z,cmap=cm.coolwarm,
#                       linewidth=0, antialiased=False)

#ax.set_xlabel('X Label')
#ax.set_ylabel('Y Label')
#ax.set_zlabel('Z Label')
#fig.colorbar(mappable=1,surf=2, shrink=0.5, aspect=5)
#fig.colorbar(im, ax=ax)


plt.show()
