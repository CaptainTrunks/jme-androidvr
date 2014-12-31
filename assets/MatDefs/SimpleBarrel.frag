/*
* fragment shader template
*/
uniform sampler2D m_Texture;
varying vec2 texCoord;
uniform float m_MaxFactor;
const float PI = 3.1415926535;

vec2 Distort(vec2 xy)
{
    float d = length(xy * m_MaxFactor);
    float z = sqrt(1.0 - d * d);
    float r = atan(d, z) / PI;
    float phi = atan(xy.y, xy.x);
    
    xy.x = r * cos(phi) + 0.5;
    xy.y = r * sin(phi) + 0.5;
    return xy;
}

void main()
{
  vec2 uv;
  vec2 xy = 2.0 * texCoord.xy - 1.0;
  float d = length(xy);
  if (d < (2.0-m_MaxFactor)){
    uv = Distort(xy);
    gl_FragColor = texture2D(m_Texture, uv);
  } else{
    gl_FragColor = vec4(0.5, 0.5, 0.5, 1.0);
    //discard;
  }
  
}
