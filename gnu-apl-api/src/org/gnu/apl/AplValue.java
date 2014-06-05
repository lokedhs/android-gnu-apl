package org.gnu.apl;

/**
 * Wrapper for the APL Value_P object.
 */
public final class AplValue
{
    private long ptr;

    protected AplValue( long ptr ) {
        this.ptr = ptr;
    }
}
